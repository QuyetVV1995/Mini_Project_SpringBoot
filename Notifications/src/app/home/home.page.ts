import { Component, OnInit } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { Plugins } from '@capacitor/core';
const {LocalNotifications} = Plugins;

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  constructor(
    private alertCtrl: AlertController
  ) {}

  async ngOnInit(){
    await LocalNotifications.requestPermission();

    LocalNotifications.registerActionTypes({
      types:[
        {
          id: 'CHAT_MSG',
        actions: [
            {
              id: 'view',
              title: 'Open Chat'
            },
            {
              id: 'remove',
              title: 'Dismiss',
              destructive: true
            },
            {
              id: 'respond',
              title: 'Respone',
              input: true
            }
        ]

      }
    ]
    })}

  async scheduleBasic(){
    await LocalNotifications.schedule({
      notifications: [{
        title: 'test notification',
        body: ' test notification body',
        id: 1,
        extra: {
          data: 'Pass data to your handler'
        },
        iconColor: '#0000FF'
      }]
    });
  }

  async scheduleAdvance(){
    await LocalNotifications.schedule({
      notifications: [
        {
          title: 'test Advance',
          body: 'body Advance',
          id: 2,
          extra: {
            data: 'Pass data to your handler'
          },
          iconColor: '#0000FF',
          actionTypeId: 'CHAT_MSG',
          attachments: [
            { id: 'face', url:'res://public/assets/logo.png'}
          ]
        }
      ]
    });
  }
}
