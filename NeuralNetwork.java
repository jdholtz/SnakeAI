public class NeuralNetwork {
    private final Neuron[] hiddenLayerNeurons;
    private final Neuron[] outputLayerNeurons;

    NeuralNetwork(double[][] hiddenLayerWeights, double[][] outputLayerWeights) {
        this.hiddenLayerNeurons = new Neuron[hiddenLayerWeights.length];
        this.outputLayerNeurons = new Neuron[outputLayerWeights.length];
        this.initializeNeurons(hiddenLayerWeights, outputLayerWeights);
    }

    private void initializeNeurons(double[][] hiddenLayerWeights, double[][] outputLayerWeights) {
        for (int i = 0; i < this.hiddenLayerNeurons.length; i++) {
            hiddenLayerNeurons[i] = new Neuron(hiddenLayerWeights[i]);
        }

        for (int i = 0; i < this.outputLayerNeurons.length; i++) {
            outputLayerNeurons[i] = new Neuron(outputLayerWeights[i]);
        }
    }

    /**
     * Returns an array of values between 0 and 1. The value that
     * is the largest is the network's best prediction for the next move
     */
    public double[] getAction(double[] inputs) {
        // First, go through the hidden layer
        double[] hiddenLayerOutputs = new double[this.hiddenLayerNeurons.length];
        for (int i = 0; i < this.hiddenLayerNeurons.length; i++) {
            hiddenLayerOutputs[i] = this.hiddenLayerNeurons[i].getOutput(inputs);
        }

        // Then, go through the output layer
        double[] outputs = new double[this.outputLayerNeurons.length];
        for (int i = 0; i < this.outputLayerNeurons.length; i++) {
            outputs[i] = this.outputLayerNeurons[i].getOutput(hiddenLayerOutputs);
        }

        return outputs;
    }
}
