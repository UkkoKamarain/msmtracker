package tools.msm.b_tracking_list.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class MsmElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idElement;
    private String nameE;
    private String imgFilePath; // temporary thingy, for if images get used by filepaths

    @ManyToMany(mappedBy = "elementsI")
    @JsonIgnore
    private List<MsmIsland> islandsE;

    @ManyToMany(mappedBy = "elementsM")
    @JsonIgnore
    private List<MsmMonster> monstersE;
    
    // CONSTRUCTORS 
    public MsmElement() {
        super();
    }

    public MsmElement(String nameE) {
        super();
        this.nameE = nameE;
    }

     public MsmElement(String nameE, String imgFilePath) {
        super();
        this.nameE = nameE;
        this.imgFilePath = imgFilePath;
    }

    public MsmElement(String nameE, List<MsmIsland> islandsE, List<MsmMonster> monstersE) {
        super();
        this.nameE = nameE;
        this.islandsE = islandsE;
        this.monstersE = monstersE;
    }

    public MsmElement(String nameE, String imgFilePath, List<MsmIsland> islandsE, List<MsmMonster> monstersE) {
        super();
        this.nameE = nameE;
        this.imgFilePath = imgFilePath;
        this.islandsE = islandsE;
        this.monstersE = monstersE;
    }
    // GETTERS & SETTERS 

    public Long getIdElement() {
        return idElement;
    }

    public void setIdElement(Long idElement) {
        this.idElement = idElement;
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE;
    }

    public String getimgFilePath() {
        return imgFilePath;
    }

    public void setimgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }

    public List<MsmIsland> getIslandsE() {
        return islandsE;
    }

    public void setIslandsE(List<MsmIsland> islandsE) {
        this.islandsE = islandsE;
    }

    public List<MsmMonster> getMonstersE() {
        return monstersE;
    }

    public void setMonstersE(List<MsmMonster> monstersE) {
        this.monstersE = monstersE;
    }

    // Pitää olla näin yksinkertainen, jotta checkboxit ja multiselectit toimii...
    @Override
    public String toString() {
        return this.nameE;
    }
    

    
}
