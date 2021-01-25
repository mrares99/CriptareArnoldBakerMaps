import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ParallelEncryption extends Thread{
    private BufferedImage colorChannel;
    private final Encryption encryption;
    private static List<BufferedImage> outputEncryptedImageList;
    private int arnoldParameterA;
    private int arnoldParameterB;

    /** Constructor.
     */
    public ParallelEncryption(){
        this.encryption=new Encryption();
        outputEncryptedImageList=new ArrayList<>();
    }

    /** Metoda pentru efectuarea crptarii in paralel.
     */
    public void run(){
        try{
            addEncryptedImageInList(encryption.doEncryption(colorChannel,arnoldParameterA,arnoldParameterB));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Retuneaza lista de imagini criptate.
     * @return Lista de imagini criptate.
     */
    public  List<BufferedImage> getOutputEncryptedImageList() {
        return outputEncryptedImageList;
    }

    /** Adauga o imagine criptata intr-o lista statica.
     * @param bufferedImage Imagine criptata.
     */
    public synchronized void addEncryptedImageInList(BufferedImage bufferedImage){
        outputEncryptedImageList.add(bufferedImage);
    }

    /** Setter - Seteaza imaginea pentru criptare.
     * @param colorChannel Imagine.
     */
    public void setColorChannel(BufferedImage colorChannel) {
        this.colorChannel = colorChannel;
    }

    /** Setter - Seteaza primul parametru al transformarii Arnold.
     * @param arnoldParameterA Parametru pentru transformarea Arnold.
     */
    public void setArnoldParameterA(int arnoldParameterA) {
        this.arnoldParameterA = arnoldParameterA;
    }

    /** Setter - Seteaza al doilea parametru al transformarii Arnold.
     * @param arnoldParameterB Parametru pentru transformarea Arnold.
     */
    public void setArnoldParameterB(int arnoldParameterB) {
        this.arnoldParameterB = arnoldParameterB;
    }
}