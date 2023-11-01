package com.ipsen.spine.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String platformName;


    public Long getId() {
            return id;
        }
    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }
}
