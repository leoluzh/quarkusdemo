package com.lambdasys.quarkusdemo.exceptions;

/**
 * leoluzh
 * @since 25/07/2020
 * @version 0.1
 */


@SuppressWarnings("serial")
public class MunicipioNotFoundException extends RuntimeException {

    public MunicipioNotFoundException( Integer id ){
        super("Could not find municipio with id: " + id );
    }

}
