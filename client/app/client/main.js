import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './main.html';

Template.hello.onCreated(function helloOnCreated() {
  // counter starts at 0
  this.counter = new ReactiveVar(0);
});

Template.hello.helpers({
  counter() {
    return Template.instance().counter.get();
  },
});

Template.hello.events({
  'click button'(event, instance) {
    // increment the counter when button is clicked
    instance.counter.set(instance.counter.get() + 1);
  },
});


Meteor.startup( function () {
  HTTP.call('POST', 'http://localhost:3000/users', {
    data: {
      name: "sangyoo",
      email: "sangyoo.km@gmail.com",
      password: "password"
    }
  }, (err, res) => {
    console.log(err, res);
  });
});
  