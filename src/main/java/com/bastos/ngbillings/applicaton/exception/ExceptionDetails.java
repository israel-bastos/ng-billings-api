package com.bastos.ngbillings.applicaton.exception;

import java.util.Date;

public record ExceptionDetails(Integer statusCode, String message, Date timestamp) {}
