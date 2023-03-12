package id.kawahedukasi.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import net.sf.jasperreports.engine.JRException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class MailService {

    @Inject
    Mailer mailer;

    @Inject
    ExportService exportService;

    public void sendEmail(String email){
        mailer.send(
                Mail.withHtml(email,
                        "CRUD API Quarkus Batch 6",
                        "<h1>Hello,</h1> this is Quarkus Item-Service\n <h1>Pusing Ceunah</h1>"));
    }

    public void sendExcelToEmail(String email) throws IOException, JRException {
        mailer.send(
                Mail.withHtml(email,
                                "Excel Peserta Batch 6",
                                "<h1>Hello,</h1> this is your excel file\n <h1>Pusing Ceunah</h1>")
                        .addAttachment("list-item.xlsx",
                                exportService.excelItem().toByteArray(),
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}
