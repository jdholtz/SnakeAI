public class NeuralNetwork {
    private final Neuron[][] neurons;

    NeuralNetwork(int numLayers, int neuronsPerLayer, double[][][] weights) {
        this.neurons = new Neuron[numLayers][neuronsPerLayer];
        this.initializeNeurons(weights);
    }

    private void initializeNeurons(double[][][] weights) {
        for (int i = 0; i < this.neurons.length; i++) {
            for (int j = 0; j < this.neurons[i].length; j++) {
                this.neurons[i][j] = new Neuron(weights[i][j]);
            }
        }
    }

    /**
     * Returns an array of values between 0 and 1. The value that
     * is the largest is the network's best prediction for the next move
     */
    public double[] getAction(double[] inputs) {
        for (Neuron[] neuronLayer : this.neurons) {
            inputs = this.getLayerOutputs(inputs, neuronLayer);
        }

        return inputs;
    }

    private double[] getLayerOutputs(double[] inputs, Neuron[] neuronLayer) {
        double[] outputs = new double[neuronLayer.length];

        for (int i = 0; i < neuronLayer.length; i++) {
            outputs[i] = neuronLayer[i].getOutput(inputs);
        }

        return outputs;
    }
}
