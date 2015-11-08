  1. Collision shape is a component = CollisionShapeC --> carries a CollisionShape | Subclasses: CollisionPolygonC, CollisionCircleC
  1. If you just want a collision shape that matches the height/width of your sprite, it will be generated automatically.
  1. To add a specific collision shape to a sprite, use the component interface and add a new CollsionShapeC(collisionshape)


Now for creating collisionShapes
  1. CollisionPolygon = polygon = defined by points
    * oints must be added in the order they would be on the perimeter of the shape
    * f they are added in the incorrect order, they will be corrected wrong, and you will not get the shape you desired.
  1. CollisionCircle = circle = defined by center and radius - self explanatory

Other Ways to make Collision Polygons
1) ShapeFactory
makePolygonFromImage(image) method
Evidently pixel perfect collisions are not working right now, USE THIS INSTEAD
This will return a polygon that fits extremely closely to the outer edge of your image
FPS PROBLEMS? Probably means you have too many complex image-based polygons - just like pixel-perfect collisions, these guys do not have the best Big O when it comes to calculating collisions
Solution: use the polygon.simplify(n) method which will return a polygon containing only every-nth vertex of the original polygon and adjust the polygon you simplified
2) use the Polygon(x ,y, sideNum, sideLength) regular shape constructor
This will construct a regular polygon with center at x,y and the input number of sides of length sidelength
ex. new Polygon(0,0,3,10) will make an equilateral triangle centered at 0,0 with side length 10
Examples
From Asteroids:
```
myShip.addComponent(new CollisionCircleC(myShip.getCenterPoint(), myShip.getWidth()/2));
```
---makes the ship have a Collision Circle of radius width/2

From Arrow:
```
new CollisionPolygonC(new CollisionPolygon(ShapeFactory.makePolygonFromImage(ImageUtil.resize(image, width,height), 2)))
```
---makes a new Collision Polygon based on the shape wrapped to the image and simplified by a factor of 2 (every other vertex removed).

To see exactly what your collision shape looks like you can utilize this code (put this in your sprite)
```
public void render(Graphics2D g, int x, int y)
	{
		AffineTransform aTransform = new AffineTransform();
        aTransform.translate((int) this.getX() +width/2, 
                             (int) this.getY()+height/2);
        aTransform.rotate(Math.toRadians(this.getAngle()+90));
        
        aTransform.translate((int) -width/2, 
                             (int) -height/2);
        if (this.getHorizontalSpeed() < 0) g.drawImage(ImageUtil.flip(ImageUtil.resize(image, width, height)),aTransform,null);
        else g.drawImage(ImageUtil.resize(image, width, height),aTransform,null);
        super.renderComponents(g, x, y);
        g.setColor(Color.YELLOW);
		this.getCollisionShape().render(g);
	}
```

that will show you the outline of your collision shape

ADDTL NOTE:
If you're looking to create a CollisionPolygon your image MUST take up the ENTIRE image file canvas.

My game, Arrow, and Asteroids both have examples of proper collision shape and CollisionGroup implementation, please check them out if you want an example immediately. Also feel free to toy around with the collision shape in asteroids to see what kinds of things you can generate...it is currently rendering its shape so you can see how they look, etc. using that as a Test harness if need be.