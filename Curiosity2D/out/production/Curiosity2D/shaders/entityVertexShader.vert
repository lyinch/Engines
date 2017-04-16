#version 410

in vec2 vertices;
in vec3 colour;
in vec2 t;
out vec2 tex;
out vec3 c_out;

uniform mat4 viewMatrix; //the camera, i.e. moves the world
uniform mat4 projectionMatrix; //scales/rotates the whole world
uniform mat4 transformationMatrix; //entity transformation matrix


void main(void){

    gl_Position =   viewMatrix * transformationMatrix * vec4(vertices,0.0,1.0);
    //gl_Position =   projectionMatrix * viewMatrix *  vec4(vertices.xy,0.0,1.0);
    c_out = colour;
    //tex = t;
}