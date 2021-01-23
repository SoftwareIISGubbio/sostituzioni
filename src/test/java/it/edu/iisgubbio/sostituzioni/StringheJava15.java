package it.edu.iisgubbio.sostituzioni;

public class StringheJava15 {
    public static void main(String[] args) {
        // https://dzone.com/articles/java-string-format-examples
        String k;

        k = String.format(
                """
                <table>                
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                    </tr>
                </table>
                """
                ,"pluto","pippo");
        
        System.out.println(k);
        
    }
}
