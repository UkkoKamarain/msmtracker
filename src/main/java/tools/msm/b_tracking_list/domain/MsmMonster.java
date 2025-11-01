package tools.msm.b_tracking_list.domain;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class MsmMonster {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMonster;
    private String nameM;
    // Mainly used for ideal breed combinations
    private String description;
    // Four(?) values, indexed in a specific order
    private List<Duration> birthTimes;
    // Relations to other Entities
    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "IslandMonsters",
        joinColumns = @JoinColumn(name = "idMonster"),
        inverseJoinColumns = @JoinColumn(name = "idIsland")
    )
    private List<MsmIsland> islandsM;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "MonsterElements",
        joinColumns = @JoinColumn(name = "idMonster"),
        inverseJoinColumns = @JoinColumn(name = "idElement")
    )
    private List<MsmElement> elementsM;

    public MsmMonster() {
        super();
        this.birthTimes = Arrays.asList(
            Duration.ofSeconds(0),
            Duration.ofSeconds(0),
            Duration.ofSeconds(0),
            Duration.ofSeconds(0));
    }

    public MsmMonster(String nameM, String description,List<MsmIsland> islandsM,
            List<MsmElement> elementsM) {
        super();
        this.birthTimes = Arrays.asList(
            Duration.ofSeconds(0),
            Duration.ofSeconds(0),
            Duration.ofSeconds(0),
            Duration.ofSeconds(0));
        this.nameM = nameM;
        this.description = description;
        this.islandsM = islandsM;
        this.elementsM = elementsM;
    }

    public MsmMonster(String nameM, String description, List<MsmIsland> islandsM,
            List<MsmElement> elementsM, List<Duration> birthTimes) {
        super();
        this.nameM = nameM;
        this.description = description;
        this.birthTimes = birthTimes;
        this.islandsM = islandsM;
        this.elementsM = elementsM;
    }

    public Long getIdMonster() {
        return idMonster;
    }

    public void setIdMonster(Long idMonster) {
        this.idMonster = idMonster;
    }

    public String getNameM() {
        return nameM;
    }

    public void setNameM(String nameM) {
        this.nameM = nameM;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Duration> getBirthTimes() {
        return birthTimes;
    }

    public void setBirthTimes(List<Duration> birthTimes) {
        this.birthTimes = birthTimes;
    }

    public List<MsmIsland> getIslandsM() {
        return islandsM;
    }

    // This is the function that returns all names 
    // of the islands that a monster is on
    public List<String> getIslandsMNames() {
        return islandsM.stream().map(i -> i.getNameI()).toList();
    }

    public void setIslandsM(List<MsmIsland> islandsM) {
        this.islandsM = islandsM;
    }

    public List<MsmElement> getElementsM() {
        return elementsM;
    }

    // This is the function that returns all names 
    // of the elements that a monster has
    public List<String> getElementsMNames() {
        return elementsM.stream().map(e -> e.getNameE()).toList();
    }

    public void setElementsM(List<MsmElement> elementsM) {
        this.elementsM = elementsM;
    }

    @Override
    public String toString() {
        return "MsmMonster [idMonster=" + idMonster + ", nameM=" + nameM + ",\ndescription=" + description
                + ",\nbirthTimes=" + birthTimes + ",\nislandsM=" + islandsM + ",\nelementsM=" + elementsM + "]";
    }
    
}
