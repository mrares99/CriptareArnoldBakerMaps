import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ParallelDecryption extends Thread{
    private BufferedImage colorChannel;
    private final Decryption decryption;
    private static List<BufferedImage> outputEncryptedImageList;
    private int arnoldParameterA;
    private int arnoldParameterB;

    /** Constructor.
     */
    public ParallelDecryption(){
        this.decryption=new Decryption();
        outputEncryptedImageList=new ArrayList<>();
    }

    /** Metoda necesara rularii in paralel a decriptarii.
     */
    public void run(){
        try{
            addEncryptedImageInList(decryption.doDecryption(colorChannel,arnoldParameterA,arnoldParameterB));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Returneaza lista de imaginii criptate.
     * @return Lista de imagini decriptate.
     */
    public  List<BufferedImage> getOutputEncryptedImageList() {
        return outputEncryptedImageList;
    }

    /** Adauga o imagine decritpata intr-o lista statica.
     * @param bufferedImage Imaginea decriptata.
     */
    public synchronized void addEncryptedImageInList(BufferedImage bufferedImage){
        outputEncryptedImageList.add(bufferedImage);
    }

    /** Setter - Seteaza imaginea pentru decriptare.
     * @param colorChannel Imagine.
     */
    public void setColorChannel(BufferedImage colorChannel) {
        this.colorChannel = colorChannel;
    }

    /** Setter - Seteaza primul parametrul pentru transformarea Arnold.
     * @param arnoldParameterA Parametru pentru transformarea Arnold.
     */
    public void setArnoldParameterA(int arnoldParameterA) {
        this.arnoldParameterA = arnoldParameterA;
    }

    /** Setter - Seteaza al doilea parametru pentru transformarea Arnold.
     * @param arnoldParameterB Parametru pentru transformarea Arnold.
     */
    public void setArnoldParameterB(int arnoldParameterB) {
        this.arnoldParameterB = arnoldParameterB;
    }
}