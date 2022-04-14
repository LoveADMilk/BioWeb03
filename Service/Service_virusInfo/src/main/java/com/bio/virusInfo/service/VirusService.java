package com.bio.virusInfo.service;

import com.bio.entityModel.model.virusInfo.Virus;

public interface VirusService {
    void insertVirus(Virus virus);

    Virus selectVirusInfoById(Integer virusId);

}
