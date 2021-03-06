import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Encryption {

    /** Genereaza secvente aleatoare pentru transformarea Arnold.
     * @param seed Valoare pe baza careia se vor genera numere pseudoaleatoare.
     * @return Sir de valori aleatoare pentru transformarea Arnold.
     * @throws NoSuchAlgorithmException
     */
    public int[] generateRandomSequenceForArnoldTransform(int seed) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
        secureRandom.setSeed(seed);
        int[] sequence=new int[6];
        for(int i=0;i<6;i++){
            sequence[i]=secureRandom.nextInt(200);
        }
        return sequence;
    }

    /** Efectueaza criptarea.
     * @param inputBufferedImage Imaginea pentru criptare.
     * @param arnoldParameterA Primul parametru pentru transformarea Arnold.
     * @param arnoldParameterB Al doilea parametru pentru transformarea Arnold.
     * @return Imaginea criptata.
     */
    public BufferedImage doEncryption(BufferedImage inputBufferedImage,int arnoldParameterA,int arnoldParameterB){
        return arnoldEncryptionTransform(inputBufferedImage,arnoldParameterA,arnoldParameterB);
    }

    /** Efectueaza transformarea Baker pe orizontala.
     * @param inputImage Imaginea pentru criptat.
     * @param widthOfInputImage Latimea imaginii.
     * @param secretKey Lista de valori necesare impartii imaginii.
     * @return Imaginea criptata orizontal.
     */
    public BufferedImage generateBakerMapOrizontal(BufferedImage inputImage,int widthOfInputImage,List<Integer> secretKey) {
        BufferedImage outputBakerMap=new BufferedImage(widthOfInputImage,widthOfInputImage,BufferedImage.TYPE_INT_RGB);
        int height,offset=widthOfInputImage,row=0,col;
        for(int i=secretKey.size()-1;i>=0;i--){
            int valForSecretKey=secretKey.get(i);
            height=widthOfInputImage/valForSecretKey-1;
            offset-=valForSecretKey;
            while(height<widthOfInputImage){
                col=0;
                for(int coordY=offset;coordY<=offset+valForSecretKey-1;coordY++){
                    for(int coordX=height;coordX>=height-widthOfInputImage/valForSecretKey+1;coordX--){
                        outputBakerMap.setRGB(col++,row,inputImage.getRGB(coordY,coordX));
                    }
                }
                row++;
                height+=widthOfInputImage/valForSecretKey;
            }
        }
        return outputBakerMap;
    }

    /** Efectueaza transformarea Baker pe verticala.
     * @param inputImage Imaginea pentru criptare.
     * @param widthOfInputImage Latimea imaginii.
     * @param secretKey Lista de valori necesare impartii imaginii.
     * @return Imaginea criptata.
     */
    public BufferedImage generateBakerMapVertical(BufferedImage inputImage,int widthOfInputImage,List<Integer> secretKey) {
        BufferedImage outputBakerMap=new BufferedImage(widthOfInputImage,widthOfInputImage,BufferedImage.TYPE_INT_RGB);
        int height,offset=widthOfInputImage,row=0,col;
        for(int i=secretKey.size()-1;i>=0;i--){
            int valForSecretKey=secretKey.get(i);
            height=widthOfInputImage/valForSecretKey-1;
            offset-=valForSecretKey;
            while(height<widthOfInputImage){
                col=0;
                for(int coordY=offset;coordY<=offset+valForSecretKey-1;coordY++){
                    for(int coordX=height;coordX>=height-widthOfInputImage/valForSecretKey+1;coordX--){
                        outputBakerMap.setRGB(col++,row,inputImage.getRGB(coordX,coordY));
                    }
                }
                row++;
                height+=widthOfInputImage/valForSecretKey;
            }
        }
        return outputBakerMap;
    }

    /** Genereaza valori aleatoare pentru impartirea imaginii.
     * @param inputNumber Latimea imaginii
     * @return Lista de valori aleatoare.
     * @throws NoSuchAlgorithmException
     */
    public List<Integer> generateSecretKey(int inputNumber) throws NoSuchAlgorithmException {
        List<Integer> outputSecretKey=new ArrayList<>();
        List<Integer> sequenceGenerated=new ArrayList<>();
        for(int i=2;i<=inputNumber/2+1;i++){
            if(inputNumber%i==0){
                outputSecretKey.add(i);
            }
        }
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG") ;
        random.setSeed(inputNumber);
        int sum=0;
        while(true){
            int value=outputSecretKey.get(random.nextInt(outputSecretKey.size()));
            if(sum+value==inputNumber){
                sequenceGenerated.add(value);
                break;
            }
            else if(sum+value<inputNumber){
                if(value%2==0){
                    sequenceGenerated.add(value);
                    sum+=value;
                }
                if(value%2==1){
                    if(sum+2*value<inputNumber){
                        sequenceGenerated.add(value);
                        sequenceGenerated.add(value);
                        sum+=2*value;
                    }
                }
            }
        }
        return sequenceGenerated;
    }

    /** Efectueaza criptarea Arnold.
     * @param inputBufferedImage Imaginea pentru criptare.
     * @param a Primul parametru necesar transformarii Arnold.
     * @param b Al doilea parametru necesar transformarii Arnold.
     * @return Imaginea criptata.
     */
    public BufferedImage arnoldEncryptionTransform(BufferedImage inputBufferedImage,int a,int b){
        int width=inputBufferedImage.getWidth();
        BufferedImage outputBufferedImage=new BufferedImage(width,width,BufferedImage.TYPE_INT_RGB);
        for(int i=-1;++i<width;){
            for(int j=-1;++j<width;){
                outputBufferedImage.setRGB(((a*b+1)*i+a*j)%width,(b*i+j)%width,inputBufferedImage.getRGB(i,j));
            }
        }
        return outputBufferedImage;
    }

}