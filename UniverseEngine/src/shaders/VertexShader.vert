#version 410
in vec3 vertices;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

out vec3 colour;


void main(void){
    gl_Position = projectionMatrix *  transformationMatrix * vec4(vertices,1.0);
    colour = vec3(1.0,0.0,0.0);
}
