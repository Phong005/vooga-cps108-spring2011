To add gravity to your game:

There should be only two steps:

  1. Add a MasslessPhysicsC component to your sprites. To do this, insert the line `addComponents(new MasslessPhysicsC());` to the constructor of your sprite.
  1. Add a "MassProportionalForceGenerator" to the instance of the physics manager which is stored in AbstractLevel. To do this, call

`(level instance).getPhysics().addGlobalForce(new MassProportionalForceGenerator(new Force(xValue, yValue)));`

where xValue would probably be 0 and yValue would probably be 9.8 in the real world, but in vooga world should probably be much smaller (try around 0.00015?). You might also need to fiddle with the sign, because I'm not sure if it gets inverted or not.


To add behavior like jumping to your sprites, call:

`VoogaPhysicsMediator.applyForceToSprite((your sprite), new MassProportionalForceGenerator(new Force(0,(some small number)), 1);`

Where (some small number) might be either positive or negative.


To stop your sprite from passing through the ground, there are unfortunately not particularly good solutions at this point. The best workaround that I've found so far is adding these two lines (assuming s1 is the sprite subject to gravity) to your BasicCollisionGroup:

`s1.setY(s2.getY()-s1.getHeight());`

`s1.setVerticalSpeed(0);`


More General Outline:

In order to implement physics, this is what needs to happen:
  1. Add any forces/vector fields you see fit to the WorldForceManager by getting the instance of the manager (the "getPhysics" method of AbstractLevel should suffice to get the manager) and adding a new force generator
  1. Create sprites with the appropriate components (for gravity, this would just be "PhysicsVelocityC"
  1. Inside of any collision group you have, call "VoogaPhysicsMediator.collision(...)", passing in the information that the method requires.


If you have other questions, ask me