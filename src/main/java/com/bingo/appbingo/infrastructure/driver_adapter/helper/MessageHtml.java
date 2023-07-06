package com.bingo.appbingo.infrastructure.driver_adapter.helper;

public class MessageHtml {

    public static String welcome(String name, String token) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Bet billions</title>\n" +
                "    <style>\n" +
                "        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap');\n" +
                "\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-image: url(https://res.cloudinary.com/ddd95zydp/image/upload/v1687887971/djfggdbmv4zeluds1yfa.png);\n" +
                "            background-repeat: no-repeat;\n" +
                "            background-size: cover;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        h1,\n" +
                "        h2,\n" +
                "        h3,\n" +
                "        p {\n" +
                "            font-family: 'Poppins', sans-serif;\n" +
                "            font-size: 16px;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            font-size: 2rem;\n" +
                "        }\n" +
                "\n" +
                "        h2 {\n" +
                "            font-size: 1rem;\n" +
                "        }\n" +
                "\n" +
                "        h3 {\n" +
                "            font-size: 16px;\n" +
                "            padding: 2rem;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "            padding: 1rem;\n" +
                "            font-family: 'Poppins', sans-serif;\n" +
                "            border-radius: 20px;\n" +
                "            background-color: #b2fbc6;\n" +
                "            cursor: pointer;\n" +
                "            margin-top: 2rem;\n" +
                "            display: inline-block;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            height: auto;\n" +
                "            max-width: 100%;\n" +
                "            display: block;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <img src=\"https://res.cloudinary.com/ddd95zydp/image/upload/v1687879055/BetBillions-images/eqktlycxsmsmakhgnr7k.png\" alt=\"Logo\">\n" +
                "            <h1>¡Hola " + name + "!</h1>\n" +
                "            <h2>Te damos la bienvenida a nuestra casa de juegos</h2>\n" +
                "            <h3>\n" +
                "                Preparate para sumergirte en una experiencia excepcional, donde entrarás\n" +
                "                en la mejor oportunidad de generar ingresos adicionales. Únete a la\n" +
                "                plataforma digital líder en juegos de azar y disfruta de la emoción en\n" +
                "                cada apuesta.\n" +
                "            </h3>\n" +
                "            <a href=\"https://betbillions.com.co/activateAccount/" + token + "\">¡Activar cuenta!</a>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public static String invalidTransaction (String user){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Bet billions</title>\n" +
                "    <style>\n" +
                "      @import url(\"https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap\");\n" +
                "\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        width: 100%;\n" +
                "        max-width: 600px;\n" +
                "        margin: 0 auto;\n" +
                "        background-image: url(https://res.cloudinary.com/ddd95zydp/image/upload/v1687887971/djfggdbmv4zeluds1yfa.png);\n" +
                "        background-repeat: no-repeat;\n" +
                "        background-size: cover;\n" +
                "      }\n" +
                "\n" +
                "      td {\n" +
                "        padding: 10px;\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      p {\n" +
                "        font-family: \"Poppins\", sans-serif;\n" +
                "        font-size: 16px;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size: 2rem;\n" +
                "      }\n" +
                "\n" +
                "      h2 {\n" +
                "        font-size: 1rem;\n" +
                "      }\n" +
                "\n" +
                "      h3 {\n" +
                "        font-size: 16px;\n" +
                "        padding: 1rem;\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "\n" +
                "      a {\n" +
                "        text-decoration: none;\n" +
                "        padding: 1rem;\n" +
                "        font-family: \"Poppins\", sans-serif;\n" +
                "        border-radius: 20px;\n" +
                "        background-color: #b2fbc6;\n" +
                "        cursor: pointer;\n" +
                "        margin-top: 2rem;\n" +
                "        display: inline-block;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        height: 250px;\n" +
                "        max-width: 100%;\n" +
                "        display: block;\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "    <table>\n" +
                "      <tr>\n" +
                "        <td>\n" +
                "          <img\n" +
                "            src=\"https://res.cloudinary.com/ddd95zydp/image/upload/v1687879055/BetBillions-images/eqktlycxsmsmakhgnr7k.png\"\n" +
                "            alt=\"Logo\"\n" +
                "          />\n" +
                "          <h1>¡Hola "+user+"!</h1>\n" +
                "          <h3>\n" +
                "            Tu transacción con el identificador\n" +
                "            <span style=\"font-weight: bolder\">649256eb9a5e832c7773b7ef</span> es\n" +
                "            inválida. Por favor, verifica el hash de la transacción y\n" +
                "            rectifícalo.\n" +
                "          </h3>\n" +
                "          <a href='https://betbillions.com.co'>ir a Bet billions</a>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }


    public static String recoverPassword(String fullName , String token){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Bet billions</title>\n" +
                "    <style>\n" +
                "      @import url(\"https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap\");\n" +
                "\n" +
                "      body {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        width: 100%;\n" +
                "        max-width: 600px;\n" +
                "        margin: 0 auto;\n" +
                "        background-image: url(https://res.cloudinary.com/ddd95zydp/image/upload/v1687887971/djfggdbmv4zeluds1yfa.png);\n" +
                "        background-repeat: no-repeat;\n" +
                "        background-size: cover;\n" +
                "      }\n" +
                "\n" +
                "      td {\n" +
                "        padding: 10px;\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      p {\n" +
                "        font-family: \"Poppins\", sans-serif;\n" +
                "        font-size: 16px;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size: 2rem;\n" +
                "      }\n" +
                "\n" +
                "      h2 {\n" +
                "        font-size: 1rem;\n" +
                "      }\n" +
                "\n" +
                "      h3 {\n" +
                "        font-size: 16px;\n" +
                "        padding: 1rem;\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "\n" +
                "      a {\n" +
                "        text-decoration: none;\n" +
                "        padding: 1rem;\n" +
                "        font-family: \"Poppins\", sans-serif;\n" +
                "        border-radius: 20px;\n" +
                "        background-color: #b2fbc6;\n" +
                "        cursor: pointer;\n" +
                "        margin-top: 2rem;\n" +
                "        display: inline-block;\n" +
                "      }\n" +
                "\n" +
                "      img {\n" +
                "        height: 250px;\n" +
                "        max-width: 100%;\n" +
                "        display: block;\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table>\n" +
                "      <tr>\n" +
                "        <td>\n" +
                "          <img\n" +
                "            src=\"https://res.cloudinary.com/ddd95zydp/image/upload/v1687879055/BetBillions-images/eqktlycxsmsmakhgnr7k.png\"\n" +
                "            alt=\"Logo\"\n" +
                "          />\n" +
                "          <h1>¡Hola " +fullName+"!</h1>\n" +
                "          <h3>\n" +
                "            Un saludo parte de bet billions, hemos recibido la solicitud de\n" +
                "            un cambio de contraseña presiona el siguiente link para realizar la\n" +
                "            actualización de tu contraseña.\n" +
                "          </h3>\n" +
                "          <a href=\"https://betbillions.com.co/updatePassword/"+token+"\">cambio de contraseña</a>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";
    }
}
