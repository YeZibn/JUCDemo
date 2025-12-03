package com.yzb.contextSwitch.waitNotify;

import java.util.LinkedList;

public class TestMessageQueue {

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(10);
    }
}

class MessageQueue {
    private LinkedList<Message> queue = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take(){
        synchronized (queue){
        while(queue.isEmpty()){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }

    public void put(Message message){
        synchronized (queue){
            while(queue.size() == capacity){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.addLast(message);
            queue.notifyAll();
        }
    }
}



class Message{
    private int id;

    private Object value;

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }
}