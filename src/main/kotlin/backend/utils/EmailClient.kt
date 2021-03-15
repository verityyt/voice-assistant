package backend.utils

import secrets.EMAIL_ADDRESS
import secrets.EMAIL_PASSWORD
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailClient {

    fun sendMail(recipient: String, subject: String, mail: String) {

        val properties = Properties()
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.host"] = "cmail01.mc-host24.de"
        properties["mail.smtp.port"] = "25"

        val myAccountEmail = EMAIL_ADDRESS
        val password = EMAIL_PASSWORD

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(myAccountEmail, password)
            }
        })

        val message = prepareMessage(session, myAccountEmail, recipient, subject, mail)
        Transport.send(message)
        println("[Email Client] Email successfully sent to $recipient")
    }

    private fun prepareMessage(
        session: Session,
        account: String,
        recipient: String,
        subject: String,
        content: String
    ): Message {

        val message = MimeMessage(session)
        message.setFrom(InternetAddress(account, VoiceAssistant.name))
        message.setRecipient(Message.RecipientType.TO, InternetAddress(recipient))
        message.subject = subject
        message.setContent(content, "text/html")
        return message
    }

}