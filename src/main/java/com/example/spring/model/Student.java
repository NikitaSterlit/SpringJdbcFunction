package com.example.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements SQLData {
   private Integer age;
   private String name;


   @Override
   public String getSQLTypeName() throws SQLException {
      return "STUDENT_TABLE";
   }

   @Override
   public void readSQL(SQLInput stream, String typeName) throws SQLException {
      this.age = stream.readInt();
      this.name = stream.readString();

   }

   @Override
   public void writeSQL(SQLOutput stream) throws SQLException {
      stream.writeInt(getAge());
      stream.writeString(getName());
   }
}