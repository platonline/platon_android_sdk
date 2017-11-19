package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.post.PlatonTransactionAdapter;
import com.platon.sdk.model.response.transaction.PlatonTransactionDetails;

/**
 * Handy callback for {@link PlatonTransactionAdapter getTransactionDetails(...)} methods
 */
public interface PlatonTransactionDetailsCallback extends PlatonBaseCallback<PlatonTransactionDetails> {
}
