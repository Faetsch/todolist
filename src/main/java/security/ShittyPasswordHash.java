package security;

import javax.security.enterprise.identitystore.PasswordHash;

public class ShittyPasswordHash implements PasswordHash
{
    @Override
    public String generate(char[] chars) {
        return null;
    }

    @Override
    public boolean verify(char[] chars, String s)
    {
        //plaintext vergleich
        return String.valueOf(chars).equals(s);
    }
}
