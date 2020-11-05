import java.util.Objects;

public class Country {
    private String country;
    private Double area;
    private Double gdp;
    private Double inflation;
    private Double lifeExpect;
    private Double military;
    private Double popGrowth;
    private Double unemployment;

    public Country(){
        this.area = 0.0;
        this.gdp = 0.0;
        this.inflation = 0.0;
        this.lifeExpect = 0.0;
        this.military = 0.0;
        this.popGrowth = 0.0;
        this.unemployment = 0.0;
    }

    public Country(String country, Double area, Double gdp, Double inflation, Double lifeExpect, Double military, Double popGrwoth, Double unemployment) {
        this.country = country;
        this.area = area;
        this.gdp = gdp;
        this.inflation = inflation;
        this.lifeExpect = lifeExpect;
        this.military = military;
        this.popGrowth = popGrwoth;
        this.unemployment = unemployment;
    }

    public String getCountry() {
        return country;
    }

    public Double getArea() {
        return area;
    }

    public Double getGdp() {
        return gdp;
    }

    public Double getInflation() {
        return inflation;
    }

    public Double getLifeExpect() {
        return lifeExpect;
    }

    public Double getMilitary() {
        return military;
    }

    public Double getPopGrowth() {
        return popGrowth;
    }

    public Double getUnemployment() {
        return unemployment;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setGdp(Double gdp) {
        this.gdp = gdp;
    }

    public void setInflation(Double inflation) {
        this.inflation = inflation;
    }

    public void setLifeExpect(Double lifeExpect) {
        this.lifeExpect = lifeExpect;
    }

    public void setMilitary(Double military) {
        this.military = military;
    }

    public void setPopGrowth(Double popGrowth) {
        this.popGrowth = popGrowth;
    }

    public void setUnemployment(Double unemployment) {
        this.unemployment = unemployment;
    }

    public Double getByNumber(int number) {
        switch (number) {
            case 0:
                return area;
            case 1:
                return gdp;
            case 2:
                return inflation;
            case 3:
                return lifeExpect;
            case 4:
                return military;
            case 5:
                return popGrowth;
            default:
                return unemployment;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country item = (Country) o;
        return this.country.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country);
    }
}
