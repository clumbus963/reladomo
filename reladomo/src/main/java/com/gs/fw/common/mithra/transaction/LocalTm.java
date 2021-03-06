/*
 Copyright 2016 Goldman Sachs.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

package com.gs.fw.common.mithra.transaction;

import javax.transaction.*;


public class LocalTm implements TransactionManager
{

    private ThreadLocal threadTransaction = new ThreadLocal();
    private ThreadLocal threadTransactionTimeout = new ThreadLocal();
    private int defaultTimeout = 60;

    public void setDefaultTimeout(int defaultTimeoutInSeconds)
    {
        this.defaultTimeout = defaultTimeoutInSeconds;
    }

    public void begin() throws NotSupportedException, SystemException
    {
        LocalTx tx = this.getLocalTransaction();
        if (tx != null)
        {
            throw new NotSupportedException("nested transactions are not supported");
        }
        tx = new LocalTx(this.getThreadTimeout(), this);
        this.threadTransaction.set(tx);
    }

    private int getThreadTimeout()
    {
        Integer timeout = (Integer) this.threadTransactionTimeout.get();
        if (timeout == null)
        {
            return this.defaultTimeout;
        }
        return timeout.intValue();
    }

    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException
    {
        LocalTx tx = getAndCheckLocalTx();
        tx.commit();
    }

    public int getStatus() throws SystemException
    {
        LocalTx tx = this.getLocalTransaction();
        if (tx == null)
        {
            return Status.STATUS_NO_TRANSACTION;
        }
        return tx.getStatus();
    }

    public Transaction getTransaction() throws SystemException
    {
        return this.getLocalTransaction();
    }

    public LocalTx getLocalTransaction()
    {
        LocalTx localTx = (LocalTx) this.threadTransaction.get();
        if (localTx != null && localTx.isAsyncCommitOrRollback())
        {
            this.threadTransaction.set(null);
            localTx = null;
        }
        return localTx;
    }

    protected void removeTransactionFromThread(LocalTx tx) throws SystemException
    {
        LocalTx threadTx = this.getLocalTransaction();
        if (threadTx != tx)
        {
            tx.setAsyncCommitOrRollback(true);
        }
        else
        {
            this.threadTransaction.set(null);
        }
    }

    public void rollback() throws IllegalStateException, SecurityException, SystemException
    {
        LocalTx tx = getAndCheckLocalTx();
        tx.rollback();
    }

    private LocalTx getAndCheckLocalTx()
    {
        LocalTx tx = this.getLocalTransaction();
        if (tx == null)
        {
            throw new IllegalStateException("no transaction associated with current thread");
        }
        return tx;
    }

    public void setRollbackOnly() throws IllegalStateException, SystemException
    {
        LocalTx tx = this.getAndCheckLocalTx();
        tx.setRollbackOnly();
    }

    public void setTransactionTimeout(int i) throws SystemException
    {
        if (i == 0)
        {
            this.threadTransactionTimeout.set(null);
        }
        this.threadTransactionTimeout.set(Integer.valueOf(i));
    }

    public void resume(Transaction transaction) throws InvalidTransactionException, IllegalStateException, SystemException
    {
        throw new RuntimeException("not implemented");
    }

    public Transaction suspend() throws SystemException
    {
        throw new RuntimeException("not implemented");
    }

    public static interface SinglePhaseResource
    {

    }
}

