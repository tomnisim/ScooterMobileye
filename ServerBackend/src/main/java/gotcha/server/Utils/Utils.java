package gotcha.server.Utils;
import gotcha.server.Config.Configuration;
import gotcha.server.Utils.Logger.SystemLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class Utils {
    private final Configuration configuration;
    private final SystemLogger systemLogger;

    @Autowired
    public Utils(Configuration configuration, SystemLogger systemLogger) {
        this.configuration = configuration;
        this.systemLogger = systemLogger;
    }

    public static String DateToString(LocalDate d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(d);
    }
//
    public static Response createResponse(Exception e) {
//        if (e instanceof DatabaseConnectionException){
//            return new Response<>("The System is not available right now, please try again later.", e);
//        }
//        if (e instanceof AdminException)
//            return new Response<>("this action wont work because of admin rules.", e);
//        if (e instanceof PurchasePolicyException)
//            return new Response<>("this purchase is violating our store policy", e);
//        if (e instanceof AlreadyRegisterdException)
//            return new Response<>("there is a user who is register to the system from this network right now.", e);
//        if (e instanceof AppointmentException)
//            return new Response<>(e.getMessage(), e);
////        if (e instanceof BasketException)
////            return new Response<>("the system cant preform this action on the shopping basket.", e);
//        if (e instanceof LoginException)
//            return new Response<>("Login failed, wrong email or password", e);
//        if (e instanceof NoPremssionException)
//            return new Response<>("you dont have permission to do this action.", e);
//        if (e instanceof NoUserRegisterdException)
//            return new Response<>("this action cannot perform by guest - please log in or register.", e);
////        if (e instanceof ObjectDoesntExsitException)
////            return new Response<>("the object doesnt Exists.", e);
////        if (e instanceof ProductAddingException)
////            return new Response<>("cant add the product.", e);
////        if (e instanceof ProductCreatingException)
////            return new Response<>("cant create the product.", e);
////        if (e instanceof PurchaseException)
////            return new Response<>("cant preform the purchase.", e);
////        if (e instanceof RegisterException)
////            return new Response<>("cant register to the system.", e);
//        if (e instanceof MarketSecuirtyException)
//            return new Response<>("this action violate our security protocols please try again.", e);
////        if (e instanceof ShippingException)
////            return new Response<>("cant ship to the desired address", e);
//        if (e instanceof StoreException)
//            return new Response<>("Store not found", e);
//        if (e instanceof StoreMethodException)
//            return new Response<>("this action wont work because of store rules", e);
////        if (e instanceof UserExcpetion)
////            return new Response<>("this action wont work because of user ruels", e);
//        if (e instanceof UserNeverBoughtInTheStoreException)
//            return new Response<>("the user never bought from the store", e);
////        if (e instanceof WrongPermterException)
////            return new Response<>("wrong parameter entered. ", e);
////        return new Response<>("the action didnt worked,try again", e);
        return new Response<>(e.getMessage(),e);
    }

    public static Date StringToDate(String s) {
        Date output = null;
        try {
            output = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (Exception e) {

        }
        return output;
    }
    public static byte[] resizeByteArray(byte[] original, int maxLength) {
        if (original.length > maxLength) {
            byte[] resized = new byte[maxLength];
            System.arraycopy(original, 0, resized, 0, maxLength);
            return resized;
        } else {
            return original;
        }
    }
    public static String generateSerialNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static byte[] getPhoto() {
        byte[] imageBytes = null;
        try {
            // Specify the URL of the image
            URL imageUrl = new URL("https://www.w3schools.com/w3images/fjords.jpg");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();

            // Read the image data into a byte array
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            imageBytes = output.toByteArray();

            // Close the input and output streams
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    public String LocalDateToString(LocalDate d) {
        return d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Date LocalDateToDate(LocalDate d) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(d.atStartOfDay(defaultZoneId).toInstant());
    }

    public String generateRandomPassword() {
        final int minLength = configuration.getMinimumPasswordLength();
        final int maxLength = configuration.getMaximumPasswordLength();
        Random random = new Random();
        int passwordLength = minLength + random.nextInt(maxLength - minLength + 1);
        StringBuilder password = new StringBuilder(passwordLength);

        // Ensure at least one lower case letter, one upper case letter, and one number
        password.append((char) ('a' + random.nextInt(26))); // lower case letter
        password.append((char) ('A' + random.nextInt(26))); // upper case letter
        password.append((char) ('0' + random.nextInt(10))); // number

        // Fill the remaining characters with random lower case letters, upper case letters, and numbers
        for (int i = 3; i < passwordLength; i++) {
            int charType = random.nextInt(3);
            if (charType == 0) {
                password.append((char) ('a' + random.nextInt(26))); // lower case letter
            } else if (charType == 1) {
                password.append((char) ('A' + random.nextInt(26))); // upper case letter
            } else {
                password.append((char) ('0' + random.nextInt(10))); // number
            }
        }

        // Shuffle the password characters to avoid a fixed pattern
        for (int i = password.length() - 1; i > 0; i--) {
            int swapIndex = random.nextInt(i + 1);
            char temp = password.charAt(swapIndex);
            password.setCharAt(swapIndex, password.charAt(i));
            password.setCharAt(i, temp);
        }

        return password.toString();
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Checks if a given string represents a number that is {@code 0<number<bound}
     *
     * @param number - the string that represents the number
     * @param bound  - the upper bound
     * @return the number that the string represents if the condition stands, or -1 otherwise
     */
    public int checkIfInBounds(String number, int bound) {
        try {
            int num = Integer.parseInt(number);
            return num > 0 && num < bound ? num : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    public void emailValidCheck(String email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new Exception("Email cannot be null");
        if (!pat.matcher(email).matches())
            throw new Exception("Invalid email");
    }

    public void passwordValidCheck(String pw) throws Exception {

        boolean containsNum = false;
        boolean containsUpper = false;
        boolean containsLower = false;
        if (pw.length() < configuration.getMinimumPasswordLength()  || pw.length() > configuration.getMaximumPasswordLength())
            throw new Exception("password length should be in range of 6-12");
        char[] pwArray = pw.toCharArray();
        for (char c : pwArray) {
            if (c >= '0' & c <= '9')
                containsNum = true;
            else if (c >= 'a' & c <= 'z')
                containsLower = true;
            else if (c >= 'A' & c <= 'Z')
                containsUpper = true;
            else
                throw new Exception("password should only upper & lower letter and digit");
        }
        if (!(containsLower && containsUpper && containsNum))
            throw new Exception("password should contain at least one upper & lower letter, and digit");
    }

    public void nameValidCheck(String name) throws Exception {
        final int MaxNamesLength = 10;
        if (name == null || name.equals(""))
            throw new Exception("Name cannot be null or empty spaces");
        //checks length of the name
        if (name.length() > MaxNamesLength)
            throw new Exception("Name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c : arrayName) {
            if (c < 'a' || c > 'z')
                throw new Exception("The name must contain letters only");
        }
    }

    public void validate_license_issue_date(LocalDate licenceIssueDate) throws Exception {
        if (licenceIssueDate.isBefore(LocalDate.now())) {
            throw new Exception("license issue date must be after current date\n");
        }
    }

    public void validate_scooter_type(String scooterType) throws Exception {
        if (scooterType == null) {
            throw new Exception("scooter type can't be null");
        }
        if (scooterType.isBlank()) {
            throw new Exception("scooter type can't be empty");
        }
    }

    public void validate_gender(String gender) throws Exception {

        if (gender == null) {
            throw new Exception("gender can't be null");
        }
        if (gender.isBlank()) {
            throw new Exception("gender can't be empty");
        }
    }

    public void validate_birth_date(LocalDate birthDay) throws Exception {
        Period p = Period.between(birthDay, LocalDate.now());
        if (p.getYears() < 16) {
            throw new Exception("Rider must be at least 16 years old");
        }
    }

    public void validate_phone_number(String phoneNumber) throws Exception {
        if (phoneNumber==null) {
            throw new Exception("phone number can't be null");
        }
        if (phoneNumber.isBlank()) {
            throw new Exception("phone number can't be blank\n");
        }
        if (phoneNumber.length() != 10) {
            throw new Exception("phone number must be of length 10\n");
        }
        if (phoneNumber.matches(".*[a-zA-Z].*"))
        {
            throw new Exception("phone number can't contain letters\n");
        }
    }

    public String send_http_post_request(String url, HashMap<String, String> postContent) {
        String answer = HttpUtility.newRequest(url,HttpUtility.METHOD_POST,postContent, new HttpUtility.Callback() {
            @Override
            public String OnSuccess(String response) {
                // on success
                systemLogger.add_log("HTTP POST On Success Response= "+response);
                return response;
            }
            @Override
            public String OnError(int status_code, String message) {
                // on error
                systemLogger.add_log("HTTP POST On Error Status Code= "+status_code+" Message= "+message);
                return message;
            }
        });
        return answer;
    }


    public int string_to_int(String str){
        int number = -1;
        number = Integer.parseInt(str);
        return number;
    }

    /**
     * this method is for the external systems!
     * @param str
     * @return true for "OK" value as well.
     */
    public static boolean string_to_boolean(String str) {
        return str.equals("t") || str.equals("T") || str.equals("true") || str.equals("TRUE") ||
                str.equals("True") || str.equals("OK") || str.equals("yes") || str.equals("YES") || str.equals("Yes");
    }

    public static LocalDate StringToLocalDate(String birthDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        // Convert String to LocalDate
        return LocalDate.parse(birthDay, formatter);
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
