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

package com.gs.fw.common.mithra.test;

import com.gs.fw.common.mithra.test.domain.InfinityTimestamp;
import com.gs.fw.common.mithra.test.domain.ParaDeskFinder;
import com.gs.fw.common.mithra.test.domain.ParaDeskList;

import java.sql.Timestamp;
import java.util.List;

public class TestGreaterThanEquals
extends TestSqlDatatypes
{

    public void testBasicGreaterThanEqualRetrieval()
            throws Exception
    {
        String sql;
        List desks;

        //Boolean : intentionally blank

        //Byte
        byte bite = -127;
        sql = "select * from PARA_DESK where LOCATION_BYTE >= -127";
        desks = new ParaDeskList(ParaDeskFinder.locationByte().greaterThanEquals(bite));
        this.genericRetrievalTest(sql, desks);

        //Char
        sql = "select * from PARA_DESK where STATUS_CHAR >= 'O'";
        desks = new ParaDeskList(ParaDeskFinder.statusChar().greaterThanEquals('O'));
        this.genericRetrievalTest(sql, desks);

        //Date
        sql = "select * from PARA_DESK where CLOSED_DATE >= '1900-01-01'";
        desks = new ParaDeskList(ParaDeskFinder.closedDate().greaterThanEquals(getDawnOfTime()));
        this.genericRetrievalTest(sql, desks);

        //Double
        sql = "select * from PARA_DESK where SIZE_DOUBLE >= 4000000000.0";
        desks = new ParaDeskList(ParaDeskFinder.sizeDouble().greaterThanEquals(4000000000.0));
        this.genericRetrievalTest(sql, desks);

        //Float
        sql = "select * from PARA_DESK where MAX_FLOAT >= 40000.0";
        desks = new ParaDeskList(ParaDeskFinder.maxFloat().greaterThanEquals((float) 40000.0));
        this.genericRetrievalTest(sql, desks);

        //Integer
        sql = "select * from PARA_DESK where TAG_INT >= 10";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(10));
        this.genericRetrievalTest(sql, desks);

        //Long
        sql = "select * from PARA_DESK where CONNECTION_LONG >= 1000000";
        desks = new ParaDeskList(ParaDeskFinder.connectionLong().greaterThanEquals(1000000));
        this.genericRetrievalTest(sql, desks);

        //Short
        sql = "select * from PARA_DESK where MIN_SHORT >= 100";
        desks = new ParaDeskList(ParaDeskFinder.minShort().greaterThanEquals((short) 100));
        this.genericRetrievalTest(sql, desks);

        //String
        sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd'";
        desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("rnd"));
        this.genericRetrievalTest(sql, desks);

        //Timestamp
        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1900-01-01 00:00:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(new Timestamp(getDawnOfTime().getTime())));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp()));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '9999-12-01 23:59:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(new Timestamp(InfinityTimestamp.getParaInfinity().getTime())));
        this.genericRetrievalTest(sql, desks);
    }

    public void testGreaterThanEqualAndEqualOnSameAttribute()
            throws Exception
    {
        String sql;
        List desks;

        //Not implemented for Boolean

        //Byte
        sql = "select * from PARA_DESK where LOCATION_BYTE >= -127 and LOCATION_BYTE = 127";
        desks = new ParaDeskList(ParaDeskFinder.locationByte().greaterThanEquals((byte) -127).and(ParaDeskFinder.locationByte().eq((byte) 127)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where LOCATION_BYTE >= -127 and LOCATION_BYTE = -127";
        desks = new ParaDeskList(ParaDeskFinder.locationByte().greaterThanEquals((byte) -127).and(ParaDeskFinder.locationByte().eq((byte) -127)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where LOCATION_BYTE >= -127 and LOCATION_BYTE = -128";
        desks = new ParaDeskList(ParaDeskFinder.locationByte().greaterThanEquals((byte) -127).and(ParaDeskFinder.locationByte().eq((byte) -128)));
        this.exactRetrievalTest(sql, desks, 0);

        //Char
        sql = "select * from PARA_DESK where STATUS_CHAR >= 'O' and STATUS_CHAR = 'P'";
        desks = new ParaDeskList(ParaDeskFinder.statusChar().greaterThanEquals('O').and(ParaDeskFinder.statusChar().eq('P')));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where STATUS_CHAR >= 'O' and STATUS_CHAR = 'O'";
        desks = new ParaDeskList(ParaDeskFinder.statusChar().greaterThanEquals('O').and(ParaDeskFinder.statusChar().eq('O')));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where STATUS_CHAR >= 'O' and STATUS_CHAR = 'M'";
        desks = new ParaDeskList(ParaDeskFinder.statusChar().greaterThanEquals('O').and(ParaDeskFinder.statusChar().eq('N')));
        this.exactRetrievalTest(sql, desks, 0);

        //Date
        sql = "select * from PARA_DESK where CLOSED_DATE >= '1981-06-08' and CLOSED_DATE = '9999-12-01'";
        desks = new ParaDeskList(ParaDeskFinder.closedDate().greaterThanEquals(getTestDate()).and(ParaDeskFinder.closedDate().eq(InfinityTimestamp.getParaInfinity())));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CLOSED_DATE >= '1981-06-08' and CLOSED_DATE = '1981-06-08'";
        desks = new ParaDeskList(ParaDeskFinder.closedDate().greaterThanEquals(getTestDate()).and(ParaDeskFinder.closedDate().eq(getTestDate())));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CLOSED_DATE >= '1981-06-08' and CLOSED_DATE = '1900-01-01'";
        desks = new ParaDeskList(ParaDeskFinder.closedDate().greaterThanEquals(getTestDate()).and(ParaDeskFinder.closedDate().eq(getDawnOfTime())));
        this.exactRetrievalTest(sql, desks, 0);

        //Double
        sql = "select * from PARA_DESK where SIZE_DOUBLE >= 4000000000.0 and SIZE_DOUBLE = 5464565234435.9";
        desks = new ParaDeskList(ParaDeskFinder.sizeDouble().greaterThanEquals(4000000000.0).and(ParaDeskFinder.sizeDouble().eq(5464565234435.9)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where SIZE_DOUBLE >= 4000000000.0 and SIZE_DOUBLE = 4000000000.0";
        desks = new ParaDeskList(ParaDeskFinder.sizeDouble().greaterThanEquals(4000000000.0).and(ParaDeskFinder.sizeDouble().eq(4000000000.0)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where SIZE_DOUBLE >= 4000000000.0 and SIZE_DOUBLE = 1564654.34";
        desks = new ParaDeskList(ParaDeskFinder.sizeDouble().greaterThanEquals(4000000000.0).and(ParaDeskFinder.sizeDouble().eq(1564654.34)));
        this.exactRetrievalTest(sql, desks, 0);

        //Float
//        Connection connection = this.getConnection();
////        sql = "select * from PARA_DESK where MAX_FLOAT >= ? and MAX_FLOAT = ?";
//        sql = "select * from PARA_DESK where MAX_FLOAT = ?";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setFloat(1, (float) 36546.43);
////        ps.setFloat(2, (float) 36546.43);
//        desks = new ParaDeskList(ParaDeskFinder.maxFloat().greaterThanEquals((float) 36546.43).and(ParaDeskFinder.maxFloat().eq((float) 36546.43)) );
//        this.genericRetrievalTest(ps, desks, connection);

//        sql = "select * from PARA_DESK where MAX_FLOAT >= 36546.43 and MAX_FLOAT = 36546.43";
//        desks = new ParaDeskList(ParaDeskFinder.maxFloat().greaterThanEquals((float) 36546.43).and(ParaDeskFinder.maxFloat().eq((float) 36546.43)));
//        this.genericRetrievalTest(sql, desks);
//
//        sql = "select * from PARA_DESK where MAX_FLOAT >= 36546.43 and MAX_FLOAT = 23423.234234234";
//        desks = new ParaDeskList(ParaDeskFinder.maxFloat().greaterThanEquals((float) 36546.43).and(ParaDeskFinder.maxFloat().eq((float) 23423.234234234)));
//        this.exactRetrievalTest(sql, desks, 0);

        //Integer
        sql = "select * from PARA_DESK where TAG_INT >= 10 and TAG_INT = 100";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(10).and(ParaDeskFinder.tagInt().eq(100)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where TAG_INT >= 10 and TAG_INT = 10";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(10).and(ParaDeskFinder.tagInt().eq(10)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where TAG_INT >= 10 and TAG_INT = 5";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(10).and(ParaDeskFinder.tagInt().eq(5)));
        this.exactRetrievalTest(sql, desks, 0);

        //Long
        sql = "select * from PARA_DESK where CONNECTION_LONG >= 2000000 and CONNECTION_LONG = 3000000 ";
        desks = new ParaDeskList(ParaDeskFinder.connectionLong().greaterThanEquals(2000000).and(ParaDeskFinder.connectionLong().eq(3000000)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CONNECTION_LONG >= 2000000 and CONNECTION_LONG = 2000000 ";
        desks = new ParaDeskList(ParaDeskFinder.connectionLong().greaterThanEquals(2000000).and(ParaDeskFinder.connectionLong().eq(2000000)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CONNECTION_LONG >= 2000000 and CONNECTION_LONG = 1000000 ";
        desks = new ParaDeskList(ParaDeskFinder.connectionLong().greaterThanEquals(2000000).and(ParaDeskFinder.connectionLong().eq(1000000)));
        this.exactRetrievalTest(sql, desks, 0);

        //Short
        sql = "select * from PARA_DESK where MIN_SHORT >= 2000 and MIN_SHORT = 3000 ";
        desks = new ParaDeskList(ParaDeskFinder.minShort().greaterThanEquals((short) 2000).and(ParaDeskFinder.minShort().eq((short) 3000)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where MIN_SHORT >= 2000 and MIN_SHORT = 2000 ";
        desks = new ParaDeskList(ParaDeskFinder.minShort().greaterThanEquals((short) 2000).and(ParaDeskFinder.minShort().eq((short) 2000)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where MIN_SHORT >= 2000 and MIN_SHORT = 1000 ";
        desks = new ParaDeskList(ParaDeskFinder.minShort().greaterThanEquals((short) 2000).and(ParaDeskFinder.minShort().eq((short) 1000)));
        this.exactRetrievalTest(sql, desks, 0);

        //String
        sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd' and DESK_ID_STRING = 'usd'";
        desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("rnd").and(ParaDeskFinder.deskIdString().eq("usd")));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd' and DESK_ID_STRING = 'rnd'";
        desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("rnd").and(ParaDeskFinder.deskIdString().eq("rnd")));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd' and DESK_ID_STRING = 'abc'";
        desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("rnd").and(ParaDeskFinder.deskIdString().eq("abc")));
        this.exactRetrievalTest(sql, desks, 0);

        //Timestamp
        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0' and CREATE_TIMESTAMP = '9999-12-01 23:59:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp()).and(ParaDeskFinder.createTimestamp().eq(new Timestamp(InfinityTimestamp.getParaInfinity().getTime()))));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0' and CREATE_TIMESTAMP = '1981-06-08 02:01:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp()).and(ParaDeskFinder.createTimestamp().eq(getTestTimestamp())));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0' and CREATE_TIMESTAMP = '1900-01-01 00:00:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp()).and(ParaDeskFinder.createTimestamp().eq(new Timestamp(getDawnOfTime().getTime()))));
        this.exactRetrievalTest(sql, desks, 0);
    }

    public void testMultipleGreaterThanEqualOnSameAttribute()
            throws Exception
    {
        String sql;
        List desks;

        //Byte
        sql = "select * from PARA_DESK where LOCATION_BYTE >= -127 and LOCATION_BYTE >= 0";
        desks = new ParaDeskList(ParaDeskFinder.locationByte().greaterThanEquals((byte) -127).and(ParaDeskFinder.locationByte().greaterThanEquals((byte) 0)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where LOCATION_BYTE >= 0 and LOCATION_BYTE >= -127";
        desks = new ParaDeskList(ParaDeskFinder.locationByte().greaterThanEquals((byte) 0).and(ParaDeskFinder.locationByte().greaterThanEquals((byte) -127)));
        this.genericRetrievalTest(sql, desks);

        //Char
        sql = "select * from PARA_DESK where STATUS_CHAR >= 'O' and STATUS_CHAR >= 'P'";
        desks = new ParaDeskList(ParaDeskFinder.statusChar().greaterThanEquals('O').and(ParaDeskFinder.statusChar().greaterThanEquals('P')));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where STATUS_CHAR >= 'O' and STATUS_CHAR >= 'P'";
        desks = new ParaDeskList(ParaDeskFinder.statusChar().greaterThanEquals('P').and(ParaDeskFinder.statusChar().greaterThanEquals('O')));
        this.genericRetrievalTest(sql, desks);

        //Date
        sql = "select * from PARA_DESK where CLOSED_DATE >= '1981-06-08' and CLOSED_DATE >= '1900-01-01'";
        desks = new ParaDeskList(ParaDeskFinder.closedDate().greaterThanEquals(getTestDate()).and(ParaDeskFinder.closedDate().greaterThanEquals(getDawnOfTime())));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CLOSED_DATE >= '1981-06-08' and CLOSED_DATE >= '1900-01-01'";
        desks = new ParaDeskList(ParaDeskFinder.closedDate().greaterThanEquals(getTestDate()).and(ParaDeskFinder.closedDate().greaterThanEquals(getDawnOfTime())));
        this.genericRetrievalTest(sql, desks);

        //Double
        sql = "select * from PARA_DESK where SIZE_DOUBLE >= 4000000000.0 and SIZE_DOUBLE >= 5464565234435.9";
        desks = new ParaDeskList(ParaDeskFinder.sizeDouble().greaterThanEquals(5464565234435.9).and(ParaDeskFinder.sizeDouble().greaterThanEquals(4000000000.0)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where SIZE_DOUBLE >= 4000000000.0 and SIZE_DOUBLE >= 5464565234435.9";
        desks = new ParaDeskList(ParaDeskFinder.sizeDouble().greaterThanEquals(4000000000.0).and(ParaDeskFinder.sizeDouble().greaterThanEquals(5464565234435.9)));
        this.genericRetrievalTest(sql, desks);

        //Float
//        sql = "select * from PARA_DESK where MAX_FLOAT >= 36546.43 and MAX_FLOAT >= 23423.234234234";
//        desks = new ParaDeskList(ParaDeskFinder.maxFloat().greaterThanEquals((float) 36546.43).and(ParaDeskFinder.maxFloat().greaterThanEquals((float) 23423.234234234)));
//        this.genericRetrievalTest(sql, desks);

//        sql = "select * from PARA_DESK where MAX_FLOAT >= 36546.43 and MAX_FLOAT >= 23423.234234234";
//        desks = new ParaDeskList(ParaDeskFinder.maxFloat().greaterThanEquals((float) 23423.234234234).and(ParaDeskFinder.maxFloat().greaterThanEquals((float) 36546.43)));
//        this.genericRetrievalTest(sql, desks);

        //Int
        sql = "select * from PARA_DESK where TAG_INT >= 10 and TAG_INT >= 5";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(10).and(ParaDeskFinder.tagInt().greaterThanEquals(5)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where TAG_INT >= 10 and TAG_INT >= 5";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(5).and(ParaDeskFinder.tagInt().greaterThanEquals(10)));
        this.genericRetrievalTest(sql, desks);

        //Long
        sql = "select * from PARA_DESK where CONNECTION_LONG >= 2000000 and CONNECTION_LONG >= 1000000 ";
        desks = new ParaDeskList(ParaDeskFinder.connectionLong().greaterThanEquals(2000000).and(ParaDeskFinder.connectionLong().greaterThanEquals(1000000)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CONNECTION_LONG >= 2000000 and CONNECTION_LONG >= 1000000 ";
        desks = new ParaDeskList(ParaDeskFinder.connectionLong().greaterThanEquals(1000000).and(ParaDeskFinder.connectionLong().greaterThanEquals(2000000)));
        this.genericRetrievalTest(sql, desks);

        //Short
        sql = "select * from PARA_DESK where MIN_SHORT >= 2000 and MIN_SHORT >= 1000 ";
        desks = new ParaDeskList(ParaDeskFinder.minShort().greaterThanEquals((short) 2000).and(ParaDeskFinder.minShort().greaterThanEquals((short) 1000)));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where MIN_SHORT >= 2000 and MIN_SHORT >= 1000 ";
        desks = new ParaDeskList(ParaDeskFinder.minShort().greaterThanEquals((short) 1000).and(ParaDeskFinder.minShort().greaterThanEquals((short) 2000)));
        this.genericRetrievalTest(sql, desks);

        //String
        sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd' and DESK_ID_STRING >= 'abc'";
        desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("rnd").and(ParaDeskFinder.deskIdString().greaterThanEquals("abc")));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd' and DESK_ID_STRING >= 'abc'";
        desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("abc").and(ParaDeskFinder.deskIdString().greaterThanEquals("rnd")));
        this.genericRetrievalTest(sql, desks);

        //Timestamp
        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0' and CREATE_TIMESTAMP >= '1900-01-01 00:00:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp()).and(ParaDeskFinder.createTimestamp().greaterThanEquals(new Timestamp(getDawnOfTime().getTime()))));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0' and CREATE_TIMESTAMP >= '1900-01-01 00:00:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.createTimestamp().greaterThanEquals(new Timestamp(getDawnOfTime().getTime())).and(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp())));
        this.genericRetrievalTest(sql, desks);
    }

    public void testGreaterThanEqualOnDifferentAttributes()
            throws Exception
    {
        String sql = "select * from PARA_DESK where DESK_ID_STRING >= 'rnd' and CREATE_TIMESTAMP >= '1900-01-01 00:00:00.0'";
        List desks = new ParaDeskList(ParaDeskFinder.deskIdString().greaterThanEquals("rnd").and(ParaDeskFinder.createTimestamp().greaterThanEquals(new Timestamp(getDawnOfTime().getTime()))));
        this.genericRetrievalTest(sql, desks);

        sql = "select * from PARA_DESK where TAG_INT >= 99 and CONNECTION_LONG >= 1000000 and CREATE_TIMESTAMP >= '1981-06-08 02:01:00.0'";
        desks = new ParaDeskList(ParaDeskFinder.tagInt().greaterThanEquals(99).and(ParaDeskFinder.connectionLong().greaterThanEquals(1000000)).and(ParaDeskFinder.createTimestamp().greaterThanEquals(getTestTimestamp())));
        this.genericRetrievalTest(sql, desks);
    }
}
