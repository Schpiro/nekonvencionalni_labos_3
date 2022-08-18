import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Normalisation();
        Normalisation.normalize();

        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null,true,5));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true,100));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),false,7));
        network.getStructure().finalizeStructure();
        network.reset();

        MLDataSet trainingSet = new BasicMLDataSet(Normalisation.INPUT, Normalisation.OUTPUT);

        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 1;
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
            if(epoch > 2000) {
                System.out.println("Previse epoha");
                break;
            }
        } while(train.getError() > 0.001);
        train.finishTraining();

        System.out.println("Unesite riujec koju zelite prevest:");
        Scanner scanner = new Scanner(System.in);
        MLData unesena_rijec = new BasicMLData(Normalisation.normalizeInput(scanner.nextLine()));
        final MLData output = network.compute(unesena_rijec);
        double max = 0;
        double pozicija_max = 0;
        int i = 0;
        System.out.println(Arrays.toString(output.getData()));
        for(var a : output.getData()){
            if(a > max){
                max = a;
                pozicija_max = i;
            }
            i++;
        }
        System.out.println("Rezultat:");
        check((int) pozicija_max);
        Encog.getInstance().shutdown();
    }
    public static void check(Integer pozicija_max){
        if(pozicija_max == 0) System.out.println("omlet");
        if(pozicija_max == 1) System.out.println("palačinka");
        if(pozicija_max == 2) System.out.println("tost");
        if(pozicija_max == 3) System.out.println("pekmez");
        if(pozicija_max == 4) System.out.println("žitarice");
        if(pozicija_max == 5) System.out.println("jogurt");
        if(pozicija_max == 6) System.out.println("pita");
    }
}
