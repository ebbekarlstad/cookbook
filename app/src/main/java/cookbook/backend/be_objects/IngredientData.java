package cookbook.backend.be_objects;

public class IngredientData {
    private String name;
    private float amount;
    private String unit;

    public IngredientData(String name, float amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }
}
