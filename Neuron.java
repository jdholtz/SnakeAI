public class Neuron {
    private final double[] weights;

    Neuron(double[] weights) {
        this.weights = weights;
    }

    public double getOutput(double[] inputs) {
        double output = this.calculateDotProduct(inputs);
        return this.sigmoid(output);
    }

    private double calculateDotProduct(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * this.weights[i];
        }

        return sum;
    }

    /**
     * Activation function for the neural network. Returns
     * a value between 0 and 1. Decides whether the neuron should
     * be "active" or not (if the prediction should be used)
     *
     * Since a neural network calculates linearly, this ensures there
     * is a non-linear output, otherwise it would give the same outputs
     * no matter how many layers it runs through.
     */
    private double sigmoid(double i) {
        return 1 / (1 + Math.exp(-1 * i));
    }
}
