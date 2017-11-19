package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.post.PlatonTransactionAdapter;
import com.platon.sdk.model.response.transaction.PlatonTransactionStatus;

/**
 * Handy callback for {@link PlatonTransactionAdapter getTransactionStatus(...)} methods
 */
public interface PlatonTransactionStatusCallback extends PlatonBaseCallback<PlatonTransactionStatus> {
}
