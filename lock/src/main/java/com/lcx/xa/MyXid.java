package com.lcx.xa;

import javax.transaction.xa.Xid;

/**
 * @author: lichunxia
 * @create: 2021-04-10 20:45
 */
public class MyXid implements Xid {

    private int formatId;
    private byte gtrid[];
    private byte bqual[];

    public MyXid(int formatId, byte[] gtrid, byte[] bqual) {
        this.formatId = formatId;
        this.gtrid = gtrid;
        this.bqual = bqual;
    }

    @Override
    public int getFormatId() {
        return formatId;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        return gtrid;
    }

    @Override
    public byte[] getBranchQualifier() {
        return bqual;
    }
}
