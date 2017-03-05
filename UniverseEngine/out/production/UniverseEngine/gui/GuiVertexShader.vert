#version 410
in vec3 vertices;
in vec3 colour;

uniform mat4 transformationMatrix; 

out vec3 out_colour;
void main(void){
    gl_Position = transformationMatrix * vec4(vertices.xy,0.0,1.0);
    out_colour = colour;
}
