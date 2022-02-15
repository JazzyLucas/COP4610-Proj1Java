package com.company;

/**
 * COP4610: Programming Project 1
 * <p>
 * Write a program in Java which creates producer and consumer threads. The
 * producer should “produce” by setting the  elements of an array of integers to
 * FULL. The consumer should “consume” by setting the elements of an array of
 * integers to EMPTY.
 *
 * @author Lucas Rendell
 * @version 2/17/2022
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ProducerConsumer pc = new ProducerConsumer();

        // Threads
        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.Produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.Consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Starts
        producerThread.start();
        consumerThread.start();

        // Joins
        producerThread.join();
        consumerThread.join();

    }//end main()
}//end Main

class ProducerConsumer extends Thread {

    LinkedList<Integer> sharedData = new LinkedList<Integer>();
    int capacity = 10;

    public void Produce() throws InterruptedException
    {
        int data = 0;
        while (true) {
            synchronized (this)
            {
                while (sharedData.size() == capacity)
                    wait();

                data+=1;
                sharedData.add(data);
                System.out.println("Producer added: " + data);

                notify();

                // Delay
                Thread.sleep(100);
            }
        }
    }//end Produce()

    public void Consume() throws InterruptedException
    {
        while (true) {
            synchronized (this)
            {
                // consumer thread waits while list
                // is empty
                while (sharedData.size() == 0)
                    wait();

                int data = sharedData.remove(0);
                System.out.println("Consumer removed: " + data);

                notify();

                // Delay
                Thread.sleep(100);
            }
        }
    }//end Consume()
}//end ProducerConsumer