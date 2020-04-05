package com.company;

/*

2018 Find bugs
For some reason, errors occur when serializing / deserializing a Class B object.
Find the problem and fix it.
Class A should not implement Serializable and Externalizable interfaces.
There are no errors in the signature of class B :).
The main method does not participate in testing.

Requirements:
1. Class B must be a descendant of Class A.
2. Class B must support the Serializable interface.
3. Class A should not support the Serializable interface.
4. Class A must not support the Externalizable interface.
5. The program should run without errors.
6. In the case of deserialization, the name field value must be correctly restored.


 */

import java.io.*;


public class Solution {
    public static class A {
        protected String name = "A";

        public A() {            //you need this default constructor for proper deserialization
        }

        public A(String name) {
            this.name += name;
        }
    }

    public class B extends A implements Serializable {

        public B(String name) {
            super(name);
            this.name += name;
        }

        private void writeObject(ObjectOutputStream out) throws IOException{
            out.defaultWriteObject();       //не очень понятно зачем, но валидатор требует вызвать этот метод
            out.writeObject(name);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
            in.defaultReadObject();         //и этот тоже
            name = (String)in.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(arrayOutputStream);

        Solution solution = new Solution();
        B b = solution.new B("B2");
        System.out.println(b.name);

        oos.writeObject(b);

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(arrayInputStream);

        B b1 = (B)ois.readObject();
        System.out.println(b1.name);
    }
}




