package com.knowledgepoker.transfer;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by starke on 15.02.2016.
 */
public class GameCreateForm {

    @NotEmpty
    private String name;

    @NotNull
    private Long createdByUserId;

    private List<Long> selectedPlayers;

    public GameCreateForm() {
       selectedPlayers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            selectedPlayers.add(0L);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getSelectedPlayers() {
        return selectedPlayers;
    }

    public void setSelectedPlayers(List<Long> selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
