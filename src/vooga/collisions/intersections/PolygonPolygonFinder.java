package vooga.collisions.intersections;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import vooga.collisions.shapes.regularShapes.*;
import vooga.util.math.LineMath;

public class PolygonPolygonFinder extends IntersectionFinder<Polygon, Polygon>
{

	public PolygonPolygonFinder()
	{
		c1 = Polygon.class;
		c2 = Polygon.class;
	}
	

    @Override
    public Intersection getIntersection (Polygon s1, Polygon s2)
    {
        Intersection in = new Intersection();
        for(Line2D L1: s1.getSides()){
            for(Line2D L2: s2.getSides()){
                if (L1.intersectsLine(L2)) {
                	Point2D intersectionPoint = LineMath.findIntersection(L1, L2);
                	if(intersectionPoint != null) in.addIntersectingPoints(intersectionPoint);
                }
            }
        }
        return in;
    }

    @Override
    public boolean areIntersecting (Polygon s1, Polygon s2)
    {
        for(Line2D L1: s1.getSides()){
            for(Line2D L2: s2.getSides()){
                if (L1.intersectsLine(L2)){
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public int compareTo(Object arg0) {
		return this.hashCode() - arg0.hashCode();
	}


}
