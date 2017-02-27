#version 410
in vec3 vertices;
in vec2 uvCoords;
in vec3 co;

uniform mat4 transformationMatrix; //transformation of the entity
uniform mat4 projectionMatrix; //projection of 3D to 2D screen
uniform mat4 viewMatrix; //the camera, i.e. moves the world

out vec2 passUV;
out vec3 vert;
out vec3 col_out;
void main(void){
    gl_Position = projectionMatrix * viewMatrix *   transformationMatrix * vec4(vertices,1.0);
    passUV = uvCoords;
    vert = vertices;
    col_out = co;
    //col_out = vec3(vertices.x+0.2,vertices.y+0.2,vertices.z+0.2);
}
