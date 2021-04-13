package com.lcx.xa;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: lichunxia
 * @create: 2021-04-10 20:48
 */
public class XaDemo {

    public static MysqlXADataSource getDataSoucrce() {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        return mysqlXADataSource;
    }

    public static void main(String[] args) {
        MysqlXADataSource dataSoucrce = getDataSoucrce();
        try {
            XAConnection xaConn1  = dataSoucrce.getXAConnection();
            XAResource xaRes1 = xaConn1.getXAResource();
            Connection conn1 = xaConn1.getConnection();
            Statement stmt1 = conn1.createStatement();
            MyXid xid1 = new MyXid(100, new byte[]{0x01}, new byte[0x02]);
            xaRes1.start(xid1, XAResource.TMNOFLAGS);
            stmt1.execute("");
            xaRes1.end(xid1, XAResource.TMSUCCESS);


            XAConnection xaConn2  = dataSoucrce.getXAConnection();
            XAResource xaRes2 = xaConn2.getXAResource();
            Connection conn2 = xaConn2.getConnection();
            Statement stmt2 = conn2.createStatement();
            MyXid xid2 = new MyXid(100, new byte[]{0x01}, new byte[0x12]);
            xaRes2.start(xid2, XAResource.TMNOFLAGS);
            stmt2.execute("");
            xaRes2.end(xid2, XAResource.TMSUCCESS);


            int rs1 = xaRes1.prepare(xid1);
            int rs2 = xaRes2.prepare(xid2);

            if (rs1 == XAResource.XA_OK && rs2 == XAResource.XA_OK) {
                xaRes1.commit(xid1, false);
                xaRes2.commit(xid2, false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (XAException e) {
            e.printStackTrace();
        }
    }
}
