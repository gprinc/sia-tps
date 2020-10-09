import java.util.Objects;

public class Country {
    private String country;
    private String area;
    private String gdp;
    private String inflation;
    private String lifeExpect;
    private String military;
    private String popGrowth;
    private String unemployment;

    public Country(String country, String area, String gdp, String inflation, String lifeExpect, String military, String popGrwoth, String unemployment) {
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

    public String getArea() {
        return area;
    }

    public String getGdp() {
        return gdp;
    }

    public String getInflation() {
        return inflation;
    }

    public String getLifeExpect() {
        return lifeExpect;
    }

    public String getMilitary() {
        return military;
    }

    public String getPopGrowth() {
        return popGrowth;
    }

    public String getUnemployment() {
        return unemployment;
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
