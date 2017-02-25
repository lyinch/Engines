#version 410
in vec3 vertices;

uniform mat4 transformationMatrix; //transformation of the entity
uniform mat4 projectionMatrix; //projection of 3D to 2D screen
uniform mat4 viewMatrix; //the camera, i.e. moves the world

out vec3 colour;


void main(void){
    gl_Position = projectionMatrix * viewMatrix *   transformationMatrix * vec4(vertices,1.0);
    
    colour = vec3(1.0,0.0,0.0);
}
