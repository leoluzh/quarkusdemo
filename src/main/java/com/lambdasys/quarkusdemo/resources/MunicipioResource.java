package com.lambdasys.quarkusdemo.resources;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import com.lambdasys.quarkusdemo.domains.Municipio;
import com.lambdasys.quarkusdemo.exceptions.MunicipioNotFoundException;
import com.lambdasys.quarkusdemo.repositories.MunicipioRepository;

@Path("/v1/municipios")
@SuppressWarnings("serial")
public class MunicipioResource implements Serializable {

    private MunicipioRepository repository;

    @Inject
    public MunicipioResource( MunicipioRepository repository ){
        this.repository = repository;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
    	summary = "Recuperar todos os municipios." ,
    	description = "Recupera todos os municipios cadastrados na base de dados."
    	
    )
    @APIResponses( value = {
    		@APIResponse( 
    				responseCode = "200" , 
    				description = "Lista de todos registros recuperados." , 
    				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
    })
    public List<Municipio> getMunicipios(){
    	List<Municipio> resultado = this.repository.findAll();
    	//System.out.println("Municipios size: " + resultado.size() );
    	return resultado;
    }

    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Recuperar municipio por identificador." ,
        	description = "Recupera um  municipios cadastrados na base de dados através de seu identificador."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Municipio com identificador selecionado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "404" , 
        				description = "Municipio não localizado com o identificador informado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public Municipio getMunicipioById( 
    		@Parameter(    				
    	            description = "Identificador do municipio.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		@PathParam("id") Integer id ) {
    	return this.repository.findById( id ).orElseThrow( () -> new MunicipioNotFoundException( id ) );
    }
    
    @DELETE
    @Path("{id}")
    @Operation(
        	summary = "Excluir municipio por identificador." ,
        	description = "Excluir um  municipios cadastrados na base de dados através de seu identificador."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Municipio excluído da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "404" , 
        				description = "Municipio não localizado com o identificador informado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public void deleteMunicipio( 
    		@Parameter(
    	            description = "Identificador do municipio.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		
    		@PathParam("id") Integer id ) {
    	this.repository.deleteById( id );
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Cadastrar municipio." ,
        	description = "Cadastrar um  municipio na base de dados."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Municipio cadastrado da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "400" , 
        				description = "Municipio não pode ser cadastrado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public Municipio newMunicipio( 
    		Municipio municipio ) {
    	return this.repository.save( municipio );
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Atualizar municipio." ,
        	description = "Atualizar um  municipio na base de dados por meio identificador informado, caso identicador não exista sistem criará um novo registro."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Municipio atualizado da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "400" , 
        				description = "Municipio não pode ser atualizado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public Municipio replaceMunicipio( 
    		@Parameter(
    	            description = "Identificador do municipio.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		
    		@PathParam("id") Integer id , Municipio newMunicipio ) {
    	
        final Municipio resultado = repository.findById( id ).map( municipio -> {
            municipio.setNome( newMunicipio.getNome() );
            municipio.setUf( newMunicipio.getUf() );
            return this.repository.save( municipio );
         }).orElseGet( () -> {
             return this.repository.save( newMunicipio );
         });
    	
        return resultado;
    	
    }
    
    @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Atualizar campos do municipio." ,
        	description = "Atualizar campos do municipio na base de dados por meio identificador informado, caso identicador não exista sistema informará erro."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Campos do municipio atualizado da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "404" , 
        				description = "Municipio não encontrado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public Municipio patchMunicipio( 
    		@Parameter(
    	            description = "Identificador do municipio.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		    		
    		@PathParam("id") Integer id , Municipio newMunicipio ) {
    	
        final Municipio resultado = repository.findById( id ).map( municipio -> {
            boolean patch = false;
            if(StringUtils.isNotBlank( newMunicipio.getNome() ) ) {
                municipio.setNome(newMunicipio.getNome());
                patch = true;
            }
            if(Objects.nonNull(newMunicipio.getUf()) ) {
                municipio.setUf(newMunicipio.getUf());
                patch = true;
            }
            if( patch ) {
                return this.repository.save(municipio);
            }else{
                return municipio;
            }
        }).orElseThrow( () -> new MunicipioNotFoundException( id ) );
    	
        return resultado;
    	
    }
        
}
