package xyz.deltacare.fatura.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import xyz.deltacare.fatura.domain.Fatura;
import xyz.deltacare.fatura.dto.EmpresaDto;

@Service
public class FaturaServiceDocCaelum implements FaturaServiceDoc {

    @Value("${aws.endpointUrl}")
    private String endpointUrl;
    @Value("${aws.bucketName}")
    private String bucketName;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;

    private final Logger logger = LoggerFactory.getLogger(FaturaServiceDocCaelum.class);

    public Fatura criar(EmpresaDto empresaDto) {

        String documento = "";
        try {

            int diaCorrente = LocalDate.now().getDayOfMonth();
            int mesCorrente = LocalDate.now().getMonthValue();
            int anoCorrente = LocalDate.now().getYear();
            int diaVencimento = 15;

            Datas datas = Datas.novasDatas()
                    .comDocumento(diaCorrente, mesCorrente, anoCorrente)
                    .comProcessamento(diaCorrente, mesCorrente, anoCorrente)
                    .comVencimento(diaVencimento, mesCorrente, anoCorrente);

            Endereco enderecoBeneficiario = Endereco.novoEndereco()
                    .comLogradouro("Av das Américas, 1093")
                    .comBairro("Barra da Tijuca")
                    .comCep("22640-101")
                    .comCidade("Rio de Janeiro")
                    .comUf("SP");

            // Quem emite o boleto
            Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                    .comNomeBeneficiario("Deltacare")
                    .comAgencia("1824").comDigitoAgencia("4")
                    .comCodigoBeneficiario("76000")
                    .comDigitoCodigoBeneficiario("5")
                    .comNumeroConvenio("1207113")
                    .comCarteira("18")
                    .comEndereco(enderecoBeneficiario)
                    .comNossoNumero("9000206");

            Endereco enderecoPagador = Endereco.novoEndereco()
                    .comLogradouro(empresaDto.getLogradouro())
                    .comBairro(empresaDto.getBairro())
                    .comCep(empresaDto.getCep())
                    .comCidade(empresaDto.getUf())
                    .comUf(empresaDto.getUf());

            //Quem paga o boleto
            Pagador pagador = Pagador.novoPagador()
                    .comNome(empresaDto.getNome())
                    .comDocumento(empresaDto.getCnpj())
                    .comEndereco(enderecoPagador);

            Banco banco = new BancoDoBrasil();

            String instrucao1 = "CÓDIGO DA EMPRESA: " + empresaDto.getCodigo();
            String instrucao2 = "APÓS VENCIMENTO: 2% DE MULTA + 0,033% DE JUROS AO DIA (SOBRE A PARCELA)";
            String instrucao3 = "SR.CAIXA, NÃO RECEBER APÓS O ÚLTIMO DIA ÚTIL DO MÊS.";

            Boleto boleto = Boleto.novoBoleto()
                    .comBanco(banco)
                    .comDatas(datas)
                    .comBeneficiario(beneficiario)
                    .comPagador(pagador)
                    .comValorBoleto("200.00")
                    .comNumeroDoDocumento("1234")
                    .comInstrucoes(instrucao1, instrucao2, instrucao3, "", "")
                    .comLocaisDePagamento("INTERNET BANKING", "LOTÉRICA");

            GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

            String nomeArquivo = empresaDto.getCodigo() + "-" + mesCorrente + "-" + anoCorrente + ".pdf";

            // Para gerar um boleto em PDF
            gerador.geraPDF(nomeArquivo);

            File file = new File(nomeArquivo);
            documento = endpointUrl + "/" + bucketName + "/" + nomeArquivo;
            subirFatura(nomeArquivo, file);
            logger.info("Fatura " + nomeArquivo + " criada.");
            file.delete();

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());

        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }

        String mesCorrente = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE,
                new Locale("pt", "BR"));

        return Fatura.builder()
                .documento(documento)
                .mes(mesCorrente)
                .build();

    }

    private void subirFatura(String fileName, File file) {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        AmazonS3 s3client = new AmazonS3Client(credentials);
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

}
