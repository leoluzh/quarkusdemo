package com.lambdasys.quarkusdemo.exceptions;

import java.io.Serializable;

/**
 * leoluzh
 * @since 25/07/2020
 * @version 0.1
 */

@SuppressWarnings("serial")
public class UnidadeFederativaNotFoundException extends RuntimeException implements Serializable {

    public UnidadeFederativaNotFoundException( Integer id ){
        super("Could not found unidade federativa with id " + id );
    }
}
