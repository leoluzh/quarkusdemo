package com.lambdasys.quarkusdemo.graphql;

import com.lambdasys.quarkusdemo.domains.Municipio;
import com.lambdasys.quarkusdemo.exceptions.MunicipioNotFoundException;
import com.lambdasys.quarkusdemo.repositories.MunicipioRepository;
import com.lambdasys.quarkusdemo.repositories.UnidadeFederativaRepository;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@GraphQLApi
@SuppressWarnings("serial")
public class MunicipioResource implements Serializable {

    // ATENCAO: Quarkus nao aceita injecao de dependencia sobre propriedades privadas/protegidas.
    @Inject
    MunicipioRepository repository;

    @Query("municipios")
    @Description("Buscar todos os municipios disponíveis.")
    public List<Municipio> getMunicipios() {
        var resultado = this.repository.findAll();
        return resultado;
    }


    @Query("municipio")
    @Description("Buscar municipio por id.")
    public Municipio getMunicipio( @Name("municipioId") int municipioId ) {
        var resultado = this.repository.findById( municipioId ).orElseThrow( () -> new MunicipioNotFoundException( municipioId ) );
        return resultado;
    }
    
}
