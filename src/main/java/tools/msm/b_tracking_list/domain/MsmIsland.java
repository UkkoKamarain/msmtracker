package tools.msm.b_tracking_list.domain;

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
public class MsmIsland {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIsland;
    private String nameI;
    private byte hatcherTier;
    private boolean seasonal;

    @ManyToMany(mappedBy = "islandsM")
    @JsonIgnore
    private List<MsmMonster> monstersI;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "IslandElements",
        joinColumns = @JoinColumn(name = "idIsland"),
        inverseJoinColumns = @JoinColumn(name = "idElement")
    )
    private List<MsmElement> elementsI;

    // CONSTRUCTORS
    public MsmIsland() {
        super();
        this.hatcherTier = 0;
        this.seasonal = false;
    }

    public MsmIsland(String nameI, List<MsmElement> elementsI) {
        super();
        this.nameI = nameI;
        this.hatcherTier = 0;
        this.seasonal = false;
        this.elementsI = elementsI; 
    }

    public MsmIsland(String nameI, byte hatcherTier, boolean seasonal,
            List<MsmElement> elementsI) {
        super();
        this.nameI = nameI;
        this.hatcherTier = hatcherTier;
        this.seasonal = seasonal;
        this.elementsI = elementsI;
    }

    public MsmIsland(String nameI, byte hatcherTier, boolean seasonal, List<MsmMonster> monstersI,
            List<MsmElement> elementsI) {
        super();
        this.nameI = nameI;
        this.hatcherTier = hatcherTier;
        this.seasonal = seasonal;
        this.monstersI = monstersI;
        this.elementsI = elementsI;
    }
    // GETTERS & SETTERS

    public Long getIdIsland() {
        return idIsland;
    }

    public void setIdIsland(Long idIsland) {
        this.idIsland = idIsland;
    }

    public String getNameI() {
        return nameI;
    }

    public void setNameI(String nameI) {
        this.nameI = nameI;
    }

    public byte getHatcherTier() {
        return hatcherTier;
    }

    public void setHatcherTier(byte hatcherTier) {
        this.hatcherTier = hatcherTier;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public List<MsmMonster> getMonstersI() {
        return monstersI;
    }

    public List<String> getMonstersINames() {
        return monstersI.stream().map(m -> m.getNameM()).toList();
    }

    public void setMonstersI(List<MsmMonster> monstersI) {
        this.monstersI = monstersI;
    }

    public List<MsmElement> getElementsI() {
        return elementsI;
    }

    public List<String> getElementsINames() {
        return elementsI.stream().map(e -> e.getNameE()).toList();
    }

    public void setElementsI(List<MsmElement> elementsI) {
        this.elementsI = elementsI;
    }

    @Override
    public String toString() {
        return this.nameI;
    }
    

    

}
