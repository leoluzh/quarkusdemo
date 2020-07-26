package com.lambdasys.quarkusdemo.repositories;

import com.lambdasys.quarkusdemo.domains.Municipio;
import com.lambdasys.quarkusdemo.domains.UnidadeFederativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * leoluzh
 * @since 25/07/2020
 * @version 0.1
 */

public interface MunicipioRepository extends JpaRepository<Municipio,Integer> {

    public List<Municipio> findByNome(String nome );
    public List<Municipio> findByUf(UnidadeFederativa uf );
    public List<Municipio> findByNomeContains( String nome );

    @Query("SELECT m FROM Municipio m WHERE m.uf.nome = '%:uf%' ")
    public List<Municipio> findByUfNomeContains( @Param("uf") String uf );

    @Query("SELECT m FROM Municipio m WHERE m.uf.id = :id ")
    public List<Municipio> findByUnidadeFederativaId( @Param("id") Integer ufId );

}
