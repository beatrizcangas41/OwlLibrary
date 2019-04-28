import static util.emailValidator.emailValidator;

public class EmailValidatorTest {

    public static void main(String[] args)
    {
        String email = "bcangas97@gmail.com";

        // Validate an email address
        if (emailValidator(email)) {
            System.out.print("The Email address " + email + " is valid");
        } else {
            System.out.print("The Email address " + email + " isn't valid");
        }
    }
}
