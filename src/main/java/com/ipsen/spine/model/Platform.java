package com.ipsen.spine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "platform")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String platformName;

//    @OneToMany
//    private set<Answer> answerset;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JsonManagedReference
//    private Answer answer;

    public Long getId() {
            return id;
        }
    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }
}
