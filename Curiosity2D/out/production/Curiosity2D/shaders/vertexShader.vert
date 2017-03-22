#version 410

in vec2 vertices;
in vec3 colour;
out vec3 c_out;

uniform mat4 viewMatrix; //the camera, i.e. moves the world
uniform mat4 projectionMatrix; //scales/rotates the whole world


void main(void){
    gl_Position =   projectionMatrix * viewMatrix * vec4(vertices.xy,0.0,1.0);
    c_out = colour;
}