package com.oscgc.securevideo.server.waad.sso;

public class FederationException extends Exception {

    private static final long serialVersionUID = -7347557105637327953L;

    public FederationException() {
        // do nothing
	}

	public FederationException(final String s) {
		super(s);
	}

	public FederationException(final Throwable throwable) {
		super(throwable);
	}

	public FederationException(final String s, final Throwable throwable) {
		super(s, throwable);
	}
}
