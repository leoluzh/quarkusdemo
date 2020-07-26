package com.lambdasys.quarkusdemo.graphql;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import com.lambdasys.quarkusdemo.domains.Municipio;
import com.lambdasys.quarkusdemo.exceptions.MunicipioNotFoundException;
import com.lambdasys.quarkusdemo.repositories.MunicipioRepository;

@GraphQLApi
@SuppressWarnings("serial")
public class MunicipioResource implements Serializable {

    // ATENCAO: Quarkus nao aceita injecao de dependencia sobre propriedades privadas/protegidas.
    private MunicipioRepository repository;

    @Inject
    public MunicipioResource( MunicipioRepository repository ) {
    	this.repository = repository;
	}

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
